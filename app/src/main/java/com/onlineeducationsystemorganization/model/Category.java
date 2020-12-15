package com.onlineeducationsystemorganization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("meesage")
@Expose
private String meesage;
@SerializedName("data")
@Expose
private List<Datum> data = null;
private final static long serialVersionUID = 6581633869545482205L;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMeesage() {
return meesage;
}

public void setMeesage(String meesage) {
this.meesage = meesage;
}

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

    public class Datum implements Serializable
    {

        @SerializedName("category_label")
        @Expose
        private String categoryLabel;
        @SerializedName("top_categories_list")
        @Expose
        private ArrayList<TopCategoriesList> topCategoriesList = null;
        @SerializedName("all_categories_list")
        @Expose
        private ArrayList<AllCategoriesList> allCategoriesList = null;
        private final static long serialVersionUID = 7883748284359938846L;

        public String getCategoryLabel() {
            return categoryLabel;
        }

        public void setCategoryLabel(String categoryLabel) {
            this.categoryLabel = categoryLabel;
        }

        public ArrayList<TopCategoriesList> getTopCategoriesList() {
            return topCategoriesList;
        }

        public void setTopCategoriesList(ArrayList<TopCategoriesList> topCategoriesList) {
            this.topCategoriesList = topCategoriesList;
        }

        public ArrayList<AllCategoriesList> getAllCategoriesList() {
            return allCategoriesList;
        }

        public void setAllCategoriesList(ArrayList<AllCategoriesList> allCategoriesList) {
            this.allCategoriesList = allCategoriesList;
        }

    }
    public class AllCategoriesList implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("sub_categories")
        @Expose
        private List<SubCategory> subCategories = null;
        private final static long serialVersionUID = -8999333791070641795L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<SubCategory> getSubCategories() {
            return subCategories;
        }

        public void setSubCategories(List<SubCategory> subCategories) {
            this.subCategories = subCategories;
        }

    }
    public class TopCategoriesList implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_img")
        @Expose
        private String categoryImg;
        private final static long serialVersionUID = -9214666157812429562L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

    }


    public class SubCategory implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;
        private final static long serialVersionUID = -2597661555035938899L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

    }

}