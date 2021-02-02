package com.wh.searchall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/2/1 13:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class File {
    private Long id;
    private String fileName;
    private String filePath;
    private Long userId;
    private String nickname;
}
