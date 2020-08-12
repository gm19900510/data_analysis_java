package com.gm.xls.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import joinery.DataFrame;

public class Demo {
	public static void main(String[] args) throws Exception {
		// 读取本地文件
		InputStream in = new FileInputStream("movies.xls");
		// 可指定XLS文件的分隔符
		DataFrame<Object> df = DataFrame.readXls(in);

		// 行数
		System.out.println("行数：" + df.length());
		// 空表判断
		System.out.println("空表判定：" + df.isEmpty());
		// 列数据类型
		System.out.println("列数据类型：" + df.types());
		// 列名
		System.out.println("列名：" + df.columns());

		// 移除指定的列
		df = df.reindex("rank");
		// 选取指定的列
		df = df.retain("name", "box_office");

		// 更改列名
		Map<Object, Object> names = new HashMap<Object, Object>();
		names.put("name", "影片名称");
		names.put("box_office", "票房");
		df.rename(names);

		// 按自定列排序，"-票房"：表示倒叙，"票房"表示正序
		df = df.sortBy("-票房");

		// 以下演示如何获取每一格的数据
		Set<Object> indexs = df.index();
		Set<Object> columns = df.columns();

		columns.stream().forEach(item -> {
			System.out.print(item + "\t");
		});
		System.out.println();
		for (Object index : indexs) {
			for (Object column : columns) {
				System.out.print(df.get(index, column));
				System.out.print("\t");
			}
			System.out.println();
		}
	}
}
