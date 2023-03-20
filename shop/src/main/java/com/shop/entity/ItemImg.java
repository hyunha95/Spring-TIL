package com.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Getter
@Setter
@NoArgsConstructor
public class ItemImg {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_img_id")
	private Long id;

	private String imgName; // 이미지 파일명
	private String oriImgName; // 원본 이미지 파일명
	private String imgUrl; // 이미지 조회 경로
	private String repImgYn; // 대표 이미지 여부

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	public ItemImg(String imgName, String oriImgName, String imgUrl) {
		this.imgName = imgName;
		this.oriImgName = oriImgName;
		this.imgUrl = imgUrl;
	}
}
