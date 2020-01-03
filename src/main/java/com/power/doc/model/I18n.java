package com.power.doc.model;

import com.power.doc.constants.DocLanguage;
import lombok.Builder;
import lombok.Data;

/**
 * @author rockychen
 * @version 1.0
 * @date 2019-12-26 16:59
 */
@Data
@Builder
public class I18n {

    private String url;
    private String type;
    private String author;
    private String contentType;
    private String description;
    private String requestHeadersTitle;
    private String requestHead;
    private String requestParameters;
    private String requestParametersHead;
    private String requestExample;
    private String responseFields;
    private String responseFieldsHead;
    private String responseExample;

    private String versionHead;
    private String errorListTitle;
    private String errorListHead;
    private String dictListTitle;
    private String dictListHead;

    public static I18n newInstance(DocLanguage docLanguage){
        if (DocLanguage.ENGLISH.code.equals(docLanguage.getCode())){
            return I18n.builder().url("URL").type("Type").author("Author").contentType("Content-Type").description("Description")
                    .requestHeadersTitle("Request-headers").requestHead("Header|Type|Description|Required|Since")
                    .requestExample("Request-example").requestParameters("Request-parameters")
                    .requestParametersHead("Parameter|Type|Description|Required|Since").responseFields("Response-fields")
                    .responseFieldsHead("Field|Type|Description|Since").responseExample("Response-example")
                    .versionHead("Version|Update Time|Status|Author|Description")
                    .errorListTitle("Error Code List").errorListHead("Error code |Description")
                    .dictListTitle("Data Dictionaries").dictListHead("Code|Type|Description").build();
        }
        return I18n.builder().url("链接").type("类型").author("作者").contentType("内容-类型").description("描述")
                    .requestHeadersTitle("请求头").requestHead("标头|类型|描述|必要|版本")
                .requestExample("请求-示例").requestParameters("请求-参数")
                .requestParametersHead("参数|类型|描述|必要|版本").responseFields("响应-字段")
                .responseFieldsHead("字段|类型|描述|版本").responseExample("响应-示例")
                .versionHead("版本|修改时间|状态|作者|描述")
                .errorListTitle("错误代码清单").errorListHead("错误代码|描述")
                .dictListTitle("数据字典").dictListHead("代码|类型|描述").build();
    }

}
