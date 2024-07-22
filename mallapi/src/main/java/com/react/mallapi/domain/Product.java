package com.react.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Table(name ="tbl_product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "imageList")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pno;

    private String pname;

    private int price;

    private boolean delFlag;

    private String pdesc;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void changePrice(int price){
        this.price =price;
    }

    public void changeName (String name){
        this.pname =name;
    }

    public void changeDesc(String desc){
        this.pdesc =desc;
    }

    public void changeDelFlag(boolean delFlag){
        this.delFlag = delFlag;
    }

    public void addImage(ProductImage image){
        image.setOrd(imageList.size());
        imageList.add(image);

    }
    public void addImageString(String fileName){
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);
    }
    public void clearList(){
        this.imageList.clear();
    }
}

