package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.dao.*;
import com.changgou.goods.pojo.*;
import com.changgou.goods.util.IdWorker;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.xml.bind.util.JAXBSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    /**
     * 数据库
     */
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Autowired
    private SkuMapper skuMapper;

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }


    /**
     * 增加
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }


    /**
     * 修改
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);

        if (!spu.getIsMarketable().equals("0")) {
            throw new RuntimeException("当前商品必须处于下架状态才能删除");
        }
        spu.setIsDelete("1");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Spu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Spu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<Spu>) spuMapper.selectAll();
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Spu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<Spu>) spuMapper.selectByExample(example);
    }

    public void checkGoods(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null) {
            throw new RuntimeException("当前商品不存在");
        }
        //判断当前商品是否为删除状态
        if ("1".equals(spu.getIsDelete())) {
            throw new RuntimeException("当前商品处于删除状态");
        }
    }

    @Override
    @Transactional
    public void audit(String id) {
        checkGoods(id);
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    @Transactional
    public void pull(String id) {
        checkGoods(id);
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    @Transactional
    public void put(String id) {
        checkGoods(id);
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //商品审核状态必须为已审核(1)
        if (!spu.getStatus().equals("1")) {
            throw new RuntimeException("当前商品未审核");
        }
        spu.setIsMarketable("1");
    }

    @Override
    @Transactional
    public void restore(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (!"1".equals(spu.getIsDelete())) {
            throw new RuntimeException("此商品未删除");
        }
        spu.setIsDelete("0");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    @Transactional
    public void readDel(String id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //判断当前商品是否处于已删除状态
        if (!"1".equals(spu.getIsDelete())) {
            throw new RuntimeException("当前商品处于未删除状态");
        }
        spuMapper.deleteByPrimaryKey(spu);
    }
    //添加sku数据

    @Override
    public void saveSkuList(Goods goods) {
        Spu spu = goods.getSpu();
        //查询分类对象
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        //查询品牌对象
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        //设置品牌与分类的关联关系
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(brand.getId());
        categoryBrand.setCategoryId(category.getId());
        int count = categoryBrandMapper.selectCount(categoryBrand);
        if (count == 0) {
            categoryBrandMapper.insert(categoryBrand);
        }
        //获取sku集合
        List<Sku> skuList = goods.getSkuList();
        if (skuList != null) {
            //遍历sku
            for (Sku sku : skuList) {
                sku.setId(String.valueOf(idWorker.nextId()));
                //设置sku规格数据
                if (StringUtils.isEmpty(sku.getSpec())) {
                    sku.setSpec("{}");
                }
                //设置sku名称
                String name = spu.getName();
                //将json转换为map,将map中value进行名称拼接
                Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                if (specMap != null && specMap.size() > 0) {
                    for (String value : specMap.values()) {
                        name += " " + value;
                    }
                }
                sku.setName(name);
                //设置spuid
                sku.setSpuId(spu.getId());
                //设置创建与修改时间
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                //商品分类id
                sku.setCategoryId(category.getId());
                //设置分类名称
                sku.setCategoryName(category.getName());
                //设置品牌名称
                sku.setBrandName(brand.getName());
                //将sku添加到数据库
                skuMapper.insertSelective(sku);
            }
        }
    }

    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 主键
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 货号
            if (searchMap.get("sn") != null && !"".equals(searchMap.get("sn"))) {
                criteria.andEqualTo("sn", searchMap.get("sn"));
            }
            // SPU名
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 副标题
            if (searchMap.get("caption") != null && !"".equals(searchMap.get("caption"))) {
                criteria.andLike("caption", "%" + searchMap.get("caption") + "%");
            }
            // 图片
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // 图片列表
            if (searchMap.get("images") != null && !"".equals(searchMap.get("images"))) {
                criteria.andLike("images", "%" + searchMap.get("images") + "%");
            }
            // 售后服务
            if (searchMap.get("saleService") != null && !"".equals(searchMap.get("saleService"))) {
                criteria.andLike("saleService", "%" + searchMap.get("saleService") + "%");
            }
            // 介绍
            if (searchMap.get("introduction") != null && !"".equals(searchMap.get("introduction"))) {
                criteria.andLike("introduction", "%" + searchMap.get("introduction") + "%");
            }
            // 规格列表
            if (searchMap.get("specItems") != null && !"".equals(searchMap.get("specItems"))) {
                criteria.andLike("specItems", "%" + searchMap.get("specItems") + "%");
            }
            // 参数列表
            if (searchMap.get("paraItems") != null && !"".equals(searchMap.get("paraItems"))) {
                criteria.andLike("paraItems", "%" + searchMap.get("paraItems") + "%");
            }
            // 是否上架
            if (searchMap.get("isMarketable") != null && !"".equals(searchMap.get("isMarketable"))) {
                criteria.andEqualTo("isMarketable", searchMap.get("isMarketable"));
            }
            // 是否启用规格
            if (searchMap.get("isEnableSpec") != null && !"".equals(searchMap.get("isEnableSpec"))) {
                criteria.andEqualTo("isEnableSpec", searchMap.get("isEnableSpec"));
            }
            // 是否删除
            if (searchMap.get("isDelete") != null && !"".equals(searchMap.get("isDelete"))) {
                criteria.andEqualTo("isDelete", searchMap.get("isDelete"));
            }
            // 审核状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }

            // 品牌ID
            if (searchMap.get("brandId") != null) {
                criteria.andEqualTo("brandId", searchMap.get("brandId"));
            }
            // 一级分类
            if (searchMap.get("category1Id") != null) {
                criteria.andEqualTo("category1Id", searchMap.get("category1Id"));
            }
            // 二级分类
            if (searchMap.get("category2Id") != null) {
                criteria.andEqualTo("category2Id", searchMap.get("category2Id"));
            }
            // 三级分类
            if (searchMap.get("category3Id") != null) {
                criteria.andEqualTo("category3Id", searchMap.get("category3Id"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }
            // 运费模板id
            if (searchMap.get("freightId") != null) {
                criteria.andEqualTo("freightId", searchMap.get("freightId"));
            }
            // 销量
            if (searchMap.get("saleNum") != null) {
                criteria.andEqualTo("saleNum", searchMap.get("saleNum"));
            }
            // 评论数
            if (searchMap.get("commentNum") != null) {
                criteria.andEqualTo("commentNum", searchMap.get("commentNum"));
            }

        }
        return example;
    }

    @Transactional
    @Override
    public void add(Goods goods) {
        Spu spu = goods.getSpu();
        long spuId = idWorker.nextId();
        spu.setId(String.valueOf(spuId));
        spu.setIsDelete("0");
        spu.setIsMarketable("0");
        spu.setStatus("0");
        spuMapper.insertSelective(spu);
        this.saveSkuList(goods);
    }

    @Transactional
    @Override
    public void update(Goods goods) {
        Spu spu = goods.getSpu();
        spuMapper.updateByPrimaryKey(spu);

        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", spu.getId());
        spuMapper.deleteByExample(example);
        saveSkuList(goods);
    }

    @Override
    public Goods findGoodsById(String id) {
        Goods goods = new Goods();
        Spu spu = spuMapper.selectByPrimaryKey(id);
        goods.setSpu(spu);
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        goods.setSkuList(skuList);
        return goods;
    }
}
