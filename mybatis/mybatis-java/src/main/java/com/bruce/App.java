package com.bruce;

import com.bruce.entity.B2bCompanyMember;
import com.bruce.mapper.IB2bCompanyMemberMapper;
import com.bruce.mapper.SysUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = factoryBuilder.build(in);

        SqlSession sqlSession = sessionFactory.openSession();

        IB2bCompanyMemberMapper mapper = sqlSession.getMapper(IB2bCompanyMemberMapper.class);
        List<B2bCompanyMember> members = mapper.findAllMember();
        System.out.println(members);

//        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
//        int delUser = userMapper.deleteById(96L);
//        System.out.println("删除SysUser:" + delUser);
         sqlSession.commit();
        // 若在线程睡眠的过程中，手动删除数据库数据，线程唤醒后再次调用相同的接口，会直接取出缓存中数据
        // 此时数据并不是最新数据，存在不一致性（并不是关闭一级缓存和二级缓存就能解决这个问题）
        // 产生的原因是 当前事务的操作与其他事务的操作存在隔离性
//        try {
//            TimeUnit.SECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("+==========================");
        // 这种方式使用的是同一个 sqlSession 与上面的是同一个事务，事务与事务之间存在隔离性
        // 故手动删除数据库数据，此时仍然查询出和上面一样的数据
//        IB2bCompanyMemberMapper mapper1 = sqlSession.getMapper(IB2bCompanyMemberMapper.class);
//        List<B2bCompanyMember> members1 = mapper1.findAllMember();
//        System.out.println(members1);

        // 若重新创建一个会话，会产生一个新的事务，读取当前数据库中最新数据
        // 但是若开启二级缓存，对同一个 namespace 下的相同查询，会去二级缓存中的数据（之前 commit 过）
        SqlSession sqlSession1 = sessionFactory.openSession();

        IB2bCompanyMemberMapper mapper1 = sqlSession1.getMapper(IB2bCompanyMemberMapper.class);
        List<B2bCompanyMember> members1 = mapper1.findAllMember();
        System.out.println(members1);

        // 一个方法中有多个操作，是一个事务（同一个SqlSession对象/Executor/Transaction/Connection）
        // 故事务就是这样操作的
        sqlSession1.commit();

        sqlSession.close();
        sqlSession1.close();
        in.close();
    }

}
