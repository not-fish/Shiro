package com;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestMD5Hash {
    public static void main(String[] args) {

        //MD5加密
        Md5Hash md5Hash = new Md5Hash("001");
        System.out.println(md5Hash.toHex());

        //MD5 + salt 加密（默认散列1次）
        md5Hash = new Md5Hash("001","01*1334px");
        System.out.println(md5Hash.toHex());

        //MD5 + salt + hash(散列1024次) 加密
        md5Hash = new Md5Hash("001","01*1334px",1024);
        System.out.println(md5Hash.toHex());
    }
}
