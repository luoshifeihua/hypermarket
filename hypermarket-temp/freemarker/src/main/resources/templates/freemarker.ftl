<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    hello,${name}
    <#list list1 as p>
        id:${p.id}
        名称:${p.name}
        生产日期:${p.birthDay?datetime}
    </#list>
</body>
</html>