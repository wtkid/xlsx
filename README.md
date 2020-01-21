> # 由于Gitee无法绑定我的OSChina账号，该工程移存到git
> Gitee地址：https://gitee.com/wt123/xslx

# xlsx
基于POI实现，注解试简易版Excel输出和解析框架，一行代码实现输出和解析。

# frame 包

Excel输出模块，包名没取好（因为最近加入了解析模块搞得不太好取名字）。

## demo
> 输出实体
```java
@Excel(type = ExcelType.XLS, sheetName = "xlsSheet", maxRow = 80)
public class XlsEntity extends TestBaseEntity {
    @CellColumn(cellType = CellType.STRING, header = "名字", order = 1, valueParser = StringValueParser.class)
    protected String name;
    @CellColumn(cellType = CellType.STRING, header = "年龄", order = 3, valueParser = DefaultValueParser.class)
    protected String age;
    @CellColumn(cellType = CellType.STRING, header = "性别", order = 2)
    protected String sex;
    @CellColumn(header = "创建时间", order = 0, dateFormat = "yyyy-MM-dd HH:mm:ss")
    protected Date date;
    @CellColumn(header = "boolean", order = 0, method = "isBt")
    protected boolean b;
    ...
}
```
> 输出核心代码

```java
List<XlsEntity> list = new ArrayList<XlsEntity>();
    for (int i = 0; i < 100; i++) {
        list.add(new XlsEntity("wt" + i, "age" + i, "sex" + i));
    }
new AnnoXlsxCreater().datas(list).createAndWrite(new FileOutputStream("D:/test/anonTest.xls"));
```

# parse 包

Excel解析模块，本来想新开个模块的，想想代码实在太少了，就放一起了。

> 解析到java实体

```java
public class ExcelReadEntity {
	@ColumnIndexMapping(cellIndex = 1)
	private String corpName;
	@ColumnIndexMapping(cellIndex = 2)
	private String businessName;
	@ColumnIndexMapping(cellIndex = 0)
	private String corpId;
	@ColumnIndexMapping(cellIndex = 3)
	private String username;
	@ColumnIndexMapping(cellIndex = 4)
	private String nickname;
	@ColumnIndexMapping(cellIndex = 5)
	private String maxDialog;
	@ColumnIndexMapping(cellIndex = 6)
	private String name;
```

> 解析核心代码

```java
List<ExcelReadEntity> entities = ExcelParseTool.parseExcel("D:\\test\\test.xlsx", ExcelReadEntity.class);
```
