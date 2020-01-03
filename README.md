<h1 align="center">Smart-Doc Project</h1>

## Update
添加模板国际化支持

[中文文档](https://github.com/shalousun/smart-doc/blob/master/README_CN.md)

## Introduce
Smart-doc is a java restful api document generation tool. Smart-doc is based on interface source code analysis to generate interface documents, and zero annotation intrusion.
You only need to write java standard comments when developing, smart-doc can help you generate a simple and clear markdown
Or a static html document. If you are tired of the numerous annotations and strong intrusion code contamination of document tools like swagger, then hug smart-doc!
## Features
- Zero annotation, zero learning cost, only need to write standard java document comments.
- Automatic derivation based on source code interface definition, powerful return structure derivation support.
- Support Spring MVC, Spring Boot, Spring Boot Web Flux (Controller mode writing).
- Supports the derivation of asynchronous interface returns such as Callable, Future, CompletableFuture.
- Support JAVA's JSR303 parameter verification specification.
- Support for automatic generation of request examples based on request parameters.
- Support for generating json return value examples.
- Support for loading source code from outside the project to generate field comments (including the sources jar package).
- Support for generating multiple formats of documents: Markdown, HTML5, Asciidoctor,Postman Collection json.
- Support for exporting error codes and data dictionary codes to API documentation.
## Getting started
[Smart-doc Samples](https://github.com/shalousun/smart-doc-demo.git)。
```
# git clone https://github.com/shalousun/smart-doc-demo.git
```
This example already provides a static html document generated in advance. You can start the Spring Boot project and then go directly to `http://localhost:8080/doc/api.html` to view the interface documentation generated by smart-doc.
Of course you can also browse `http://localhost:8080/doc/api.html`, 
which looks a html like generated by `asciidoctor-maven-plugin` plugin.
### Dependency
#### maven
```
<dependency>
    <groupId>com.github.shalousun</groupId>
    <artifactId>smart-doc</artifactId>
    <version>1.8.0</version>
    <scope>test</scope>
</dependency>
```
#### gradle
```
testCompile 'com.github.shalousun:smart-doc:1.8.0'
```
### Create a unit test
Just running a unit test will allow Smart-doc to generate a very concise api document for you, 
which is much simpler than swagger.

```
public class ApiDocTest {

    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        
        //If the strict mode is set to true, Smart-doc forces that the public method in each interface in the code has a comment.
        config.setStrict(true);
        
        //When AllInOne is set to true, the document generation of all interfaces is merged into a Markdown or AsciiDoc document,
        // and the error code list is output to the bottom of the document.
        config.setAllInOne(true);
        
        //Set the api document output path.
        config.setOutPath("d:\\md");
        
        //Generating Markdown documentation
        ApiDocBuilder.builderControllersApi(config);
    }
}
```
**Detailed use case:**
```
public class ApiDocTest {

    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        config.setServerUrl("http://localhost:8080");
        
        //If the strict mode is set to true, Smart-doc forces that the public method in each interface in the code has a comment.
        config.setStrict(true);
        
        //When AllInOne is set to true, the document generation of all interfaces is merged into a Markdown or AsciiDoc document,
        // and the error code list is output to the bottom of the document.
        config.setAllInOne(true);
        
        //Set the api document output path.
        config.setOutPath("d:\\md");
        
        //since 1.7.5
        //corverd old AllIneOne.md  file generated by smart-doc.
        config.setCoverOld(true);
        
        //since 1.7.5
        //set project name
        config.setProjectName("Your project name");
        // If you do not configure PackageFilters, it matches all controllers by default.
        // Configure multiple controller paths to be separated by commas
        config.setPackageFilters("com.power.doc.controller");
        
        //Set the request header, if there is no request header, you don't need to set it.
        config.setRequestHeaders(
                ApiReqHeader.header().setName("access_token").setType("string")
                        .setDesc("Basic auth credentials").setRequired(true).setSince("v1.1.0"),
                ApiReqHeader.header().setName("user_uuid").setType("string").setDesc("User Uuid key")
        );
        
        //Output a list of error codes in the project, using for example:
        List<ApiErrorCode> errorCodeList = new ArrayList<>();
        for (ErrorCodeEnum codeEnum : ErrorCodeEnum.values()) {
            ApiErrorCode errorCode = new ApiErrorCode();
            errorCode.setValue(codeEnum.getCode()).setDesc(codeEnum.getDesc());
            errorCodeList.add(errorCode);
        }
        //not necessary
        config.setErrorCodes(errorCodeList);
    
        //Set the document change record,
        //it is not necessary to have the document change record take effect only when setAllInOne is set to true.
        config.setRevisionLogs(
                RevisionLog.getLog().setRevisionTime("2018/12/15").setAuthor("chen").setRemarks("test").setStatus("create").setVersion("V1.0"),
                RevisionLog.getLog().setRevisionTime("2018/12/16").setAuthor("chen2").setRemarks("test2").setStatus("update").setVersion("V2.0")
        );
        
        //since 1.7.5
        //add data dictionary
        config.setDataDictionaries(
            ApiDataDictionary.dict().setTitle("Order status").setEnumClass(OrderEnum.class).setCodeField("code").setDescField("desc"),
            ApiDataDictionary.dict().setTitle("Order status1").setEnumClass(OrderEnum.class).setCodeField("code").setDescField("desc")
        );
        //Generating Markdown documentation
        ApiDocBuilder.builderControllersApi(config);
    }
}
```
### Generated document example
#### Interface header rendering
![header](https://images.gitee.com/uploads/images/2019/1231/223538_be45f8a9_144669.png "header.png")
#### Request parameter example rendering
![request-params](https://images.gitee.com/uploads/images/2019/1231/223710_88933f55_144669.png "request.png")
#### Response parameter example renderings
![response-fields](https://images.gitee.com/uploads/images/2019/1231/223817_32bea6dc_144669.png "response.png")
## Use Maven Plugin
Smart-doc provides a maven plugin to quickly integrate into the project to generate documentation.
 You can use the plugin instead of the unit test above. [smart-doc-maven-plugin](https://github.com/shalousun/smart-doc-maven-plugin)
## Building
you can build with the following commands. (Java 1.8 is required to build the master branch)
```
mvn clean install -Dmaven.test.skip=true
```
## Other reference
- [Smart-doc manual](https://github.com/shalousun/smart-doc/wiki)

## Who is using
These are only part of the companies using smart-doc, for reference only. If you are using smart-doc, please [add your company here](https://github.com/shalousun/smart-doc/issues/12) to tell us your scenario to make smart-doc better.

![iFLYTEK](https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/iflytek.png)
![OnePlus](https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/oneplus.png)
![Xiaomi](https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/xiaomi.png)
![远盟健康](https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/yuanmengjiankang.png)
![上海普彻信息科技](https://raw.githubusercontent.com/shalousun/smart-doc/dev/images/known-users/puqie_gaitubao_100x100.jpg)
## License
Smart-doc is under the Apache 2.0 license.  See the [LICENSE](https://github.com/shalousun/smart-doc/blob/master/license.txt) file for details.
## Contact
Email： 836575280@qq.com
