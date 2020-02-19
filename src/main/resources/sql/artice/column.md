sample
===

	select #use("cols")# from COLUMN_T cln where  #use("condition")#

sample$count
===
    select count(1) from COLUMN_T cln where #use("condition")#

cols
===
	cln.URL,cln.ID,cln.DE_AT,cln.PARENT_ID,cln.DE_BY,cln.UP_AT,cln.CR_BY,cln.CR_AT,cln.UP_BY,cln.NAME,cln.ORDER,cln.KEYS,cln.DESCRIBE,cln.THUMBNAIL,cln.MGT_STYLE,cln.LIST_TPL,cln.DETAIL_TPL

updateSample
===

	cln.URL=#url#,cln.ID=#id#,cln.DE_AT=#deAt#,cln.PARENT_ID=#parentId#,cln.DE_BY=#deBy#,cln.UP_AT=#upAt#,cln.CR_BY=#crBy#,cln.CR_AT=#crAt#,cln.UP_BY=#upBy#,cln.NAME=#name#,cln.ORDER=#order#,cln.KEYS=#keys#,cln.DESCRIBE=#describe#,cln.THUMBNAIL=#thumbnail#,cln.MGT_STYLE=#mgtStyle#,cln.LIST_TPL=#listTpl#,cln.DETAIL_TPL=#detailTpl#
	
condition
===

	1 = 1 and cln.DE_AT is null
	@if(!isEmpty(id)){
	 and cln.ID=#id#
	@}
	@if(!isEmpty(deAt)){
	 and cln.DE_AT=#deAt#
	@}
	@if(!isEmpty(parentId)){
	 and cln.PARENT_ID=#parentId#
	@}
	@if(!isEmpty(deBy)){
	 and cln.DE_BY=#deBy#
	@}
	@if(!isEmpty(upAt)){
	 and cln.UP_AT=#upAt#
	@}
	@if(!isEmpty(crBy)){
	 and cln.CR_BY=#crBy#
	@}
	@if(!isEmpty(crAt)){
	 and cln.CR_AT=#crAt#
	@}
	@if(!isEmpty(upBy)){
	 and cln.UP_BY=#upBy#
	@}
	@if(!isEmpty(name)){
	 and cln.NAME=#name#
	@}
	@if(!isEmpty(order)){
     and cln.ORDER=#order#
    @}
    @if(!isEmpty(keys)){
     and cln.KEYS=#keys#
    @}
    @if(!isEmpty(describe)){
     and cln.DESCRIBE=#describe#
    @}
    @if(!isEmpty(thumbnail)){
     and cln.THUMBNAIL=#thumbnail#
    @}
    @if(!isEmpty(mgtStyle)){
     and cln.MGT_STYLE=#mgtStyle#
    @}
    @if(!isEmpty(listTpl)){
     and cln.LIST_TPL=#listTpl#
    @}    
    @if(!isEmpty(detailTpl)){
     and cln.DETAIL_TPL=#detailTpl#
    @} 
    @if(!isEmpty(url)){
     and cln.URL=#url#
    @} 
    
 
topLevelAllData
===
-- 顶级数据 全数据查询，包括上级 和下级数据

    select #use("cols")#
    @orm.many({"id":"columnId"},"artice.column.selectColumnTagDictItem","com.neuray.wp.entity.DictItem",{"alias":"tags"});
    from COLUMN_T cln where  #use("condition")# and cln.PARENT_ID is null order by cln.order
    
    
selectColumnTagDictItem
===
-- 查询栏目关联的标签数据
    
    select di.*  from COLUMN_TAG_T ct left join DICT_ITEM_T di on ct.TAG_ID=di.id where ct.COLUMN_ID=#columnId#
	

selectColumnAllParent
===
-- 根据id查询所有的上级，递归方式
	select * from COLUMN_T where DE_AT is null and ID=#cId#
	@orm.many({"id":"cId"},"artice.column.selectFromBottomLvl","Column",{"alias":"allParents"});

selectFromBottomLvl
===
-- 从子栏目 向上递归查询

	select T1.parent_id as id ,T1.name from (
	select
		\@r as _id,
		(
		select
			\@r := parent_id
		from
			COLUMN_T
		where
			id = _id and DE_AT is null) as parent_id,
			(
		select
			\@q := name
		from
			COLUMN_T
		where
			id = _id and DE_AT is null) as name,
		\@l := \@l + 1 as lvl
	from
		(
		select
			\@r := #cId#,
			\@l := 0) vars,
		COLUMN_T h
	where
		\@r <> 0)T1 join COLUMN_T T2 on T1._id = T2.id  where T1.parent_id is not null

    
