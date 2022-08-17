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

public class App {

    public static void main(String[] args) throws IOException {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = factoryBuilder.build(in);

        SqlSession sqlSession = sessionFactory.openSession();

        IB2bCompanyMemberMapper mapper = sqlSession.getMapper(IB2bCompanyMemberMapper.class);
        List<B2bCompanyMember> members = mapper.findAllMember();
        System.out.println(members);

        SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
        int delUser = userMapper.deleteById(96L);
        System.out.println("删除SysUser:" + delUser);

        // 一个方法中有多个操作，是一个事务（同一个SqlSession对象/Executor/Transaction/Connection）
        // 故事务就是这样操作的
        sqlSession.commit();

        sqlSession.close();
        in.close();
    }

}
