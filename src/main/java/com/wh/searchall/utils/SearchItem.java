package com.wh.searchall.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchItem implements Serializable {
  @Field("blog_id")
  private String id;
  @Field
  private String blog_title;
  private List<String> highLight;
  @Field
  private String blog_update_time;
//  @Field
//  private String blog_content;
  @Field
  private String blog_description;
  @Field
  private String blog_first_picture;
  @Field
  public String blog_user_id;
  @Field
  private String blog_user_avatar;
  @Field
  private String blog_user_nickname;
  @Field("path")
  private String path;
  @Field
  private Date pathuploaddate;


}