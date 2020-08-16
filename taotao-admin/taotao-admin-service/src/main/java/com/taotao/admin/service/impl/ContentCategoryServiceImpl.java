package com.taotao.admin.service.impl;

import com.taotao.admin.mapper.ContentCatogeryMapper;
import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: taotao-admin
 * @description: 商品分类接口实现类-实现类
 * @author: lhy
 * @create: 2020-07-20 20:45
 **/
@Service
@Transactional(readOnly = false)
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {

    //注入查询接口
    @Autowired
    private ContentCatogeryMapper contentCatogeryMapper;

    @Override
    public List<Map<String, Object>> findContentCategoryByParentId(Long parentId) {
        List<Map<String, Object>> contentCategorys = contentCatogeryMapper.getContentCategoryByParentId(parentId);
        for (Map<String,Object> map: contentCategorys) {
            boolean state = (boolean)map.get("state");
            map.put("state",state ? "closed" : "open");
        }
        return contentCategorys;
    }

    /**
    * @Description:
    * @Param:
    * @return:
    */
    @Override
    public Long saveContentCategory(ContentCategory contentCategory) {
        //添加新的分类
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(contentCategory.getCreated());
        contentCatogeryMapper.insertSelective(contentCategory);

//        更新父类等级
        ContentCategory parentContentCategory = new ContentCategory();

        parentContentCategory.setId(contentCategory.getParentId());
        parentContentCategory.setUpdated(new Date());
        parentContentCategory.setIsParent(true);
        contentCatogeryMapper.updateByPrimaryKeySelective(parentContentCategory);

        return contentCategory.getId();
    }

    @Override
    public void deleteContentCategory(ContentCategory contentCategory) {
            try {
                //        定义集合封装需要删除的树节点
                List<Long> ids = new ArrayList<>();
//        添加自身id
                ids.add(contentCategory.getId());
//        递归获取该节点下，所有子元素id
                findLeafNode(ids,contentCategory.getId());
//        删除所有树节点
             //   this.deleteAll("id",ids.toArray(new Long[ids.size()]));
                for (Long id : ids){
                    contentCatogeryMapper.deleteByPrimaryKey(id);
                }
//        判断当前节点是否还有子节点，如果没有子节点，父节点变成叶节点
                ContentCategory parent = new ContentCategory();
                parent.setParentId(contentCategory.getParentId());
                int count = contentCatogeryMapper.selectCount(parent);
                if(count==0){
//            修改父节点为叶节点
                    ContentCategory parentz = new ContentCategory();
                    parentz.setId(contentCategory.getParentId());
                    parentz.setIsParent(false);
                    parentz.setUpdated(new Date());
                    updateSelective(parentz);
                }
            }catch (Exception exception){
                throw new RuntimeException(exception);
            }
    }
    /**
    * @Description:
    * @Param:
    * @return:
    */
    private void findLeafNode(List<Long> ids,Long id){
//        创建内容分类对象
        ContentCategory contentCategory = new ContentCategory();
//        添加查询条件
        contentCategory.setParentId(id);
//        根据父节点查询所有的子节点
        List<ContentCategory> lists = contentCatogeryMapper.select(contentCategory);
//        设置递归退出条件
        if(lists != null && lists.size()>0){
            for(ContentCategory cc:lists){
                ids.add(cc.getId());
                findLeafNode(ids,cc.getId());
            }
        }
    }
}
