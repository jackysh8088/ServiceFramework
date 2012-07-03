package com.example.model;

import net.csdn.BaseServiceWithIocTest;
import net.csdn.jpa.JPA;
import net.csdn.validate.ValidateResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static net.csdn.common.collections.WowCollections.newHashMap;

/**
 * User: WilliamZhu
 * Date: 12-7-1
 * Time: 下午2:26
 */
public class BlogTest extends BaseServiceWithIocTest {

    @Test
    public void testValidate() throws Exception {
        Blog blog = Blog.create(newHashMap("id", 1));
        Assert.assertTrue(blog.valid() == false);
        List<ValidateResult> validateResults = blog.validateResults;
        Assert.assertTrue(validateResults.size() == 1);
        Assert.assertTrue(validateResults.get(0).getMessage().equals("content不能为空"));

        blog = Blog.create(newHashMap("id", 1, "content", "----"));
        Assert.assertTrue(blog.valid() == true);
    }

    @Test
    public void testName() throws Exception {
        Blog.deleteAll();

        long count = Blog.count();
        Assert.assertTrue(count == 0);

        Blog blog = Blog.create(newHashMap("id", 1, "content", "wow"));
        blog.save();

        Blog blogFromDb = Blog.findById(1);
        Assert.assertTrue("wow".equals(blogFromDb.attr("content", String.class)));

        blog.delete();

        Blog.create(newHashMap("id", 3, "content", "wow")).save();
        Blog.create(newHashMap("id", 4, "content", "wow")).save();
        List<Blog> blogList = Blog.where("id>2").fetch();
        Assert.assertTrue(blogList.size() == 2);

        Blog.deleteAll();

        JPA.getJPAConfig().getJPAContext().closeTx(false);


    }
}
