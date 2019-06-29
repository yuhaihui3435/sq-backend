sample
===

	select #use("cols")# from COLUMN_T column where  #use("condition")#

sample$count
===
    select count(1) from COLUMN_T column where #use("condition")#

cols
===
	column.ID,column.DE_AT,column.PARENT_ID,column.DE_BY,column.UP_AT,column.CR_BY,column.CR_AT,column.UP_BY,column.NAME,column.ORDER,column.KEYS,column.DESCRIBE,column.THUMBNAIL,column.MGT_STYLE,column.LIST_TPL,column.DETAIL_TPL

updateSample
===

	column.ID=#id#,column.DE_AT=#deAt#,column.PARENT_ID=#parentId#,column.DE_BY=#deBy#,column.UP_AT=#upAt#,column.CR_BY=#crBy#,column.CR_AT=#crAt#,column.UP_BY=#upBy#,column.NAME=#name#,column.ORDER=#order#,column.KEYS=#keys#,column.DESCRIBE=#describe#,column.THUMBNAIL=#thumbnail#,column.MGT_STYLE=#mgtStyle#,column.LIST_TPL=#listTpl#,column.DETAIL_TPL=#detailTpl#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(id)){
	 and column.ID=#id#
	@}
	@if(!isEmpty(deAt)){
	 and column.DE_AT=#deAt#
	@}
	@if(!isEmpty(parentId)){
	 and column.PARENT_ID=#parentId#
	@}
	@if(!isEmpty(deBy)){
	 and column.DE_BY=#deBy#
	@}
	@if(!isEmpty(upAt)){
	 and column.UP_AT=#upAt#
	@}
	@if(!isEmpty(crBy)){
	 and column.CR_BY=#crBy#
	@}
	@if(!isEmpty(crAt)){
	 and column.CR_AT=#crAt#
	@}
	@if(!isEmpty(upBy)){
	 and column.UP_BY=#upBy#
	@}
	@if(!isEmpty(name)){
	 and column.NAME=#name#
	@}
	@if(!isEmpty(order)){
     and column.ORDER=#order#
    @}
    @if(!isEmpty(keys)){
     and column.KEYS=#keys#
    @}
    @if(!isEmpty(describe)){
     and column.DESCRIBE=#describe#
    @}
    @if(!isEmpty(thumbnail)){
     and column.THUMBNAIL=#thumbnail#
    @}
    @if(!isEmpty(mgtStyle)){
     and column.MGT_STYLE=#mgtStyle#
    @}
    @if(!isEmpty(listTpl)){
     and column.LIST_TPL=#listTpl#
    @}    
    @if(!isEmpty(detailTpl)){
     and column.DETAIL_TPL=#detailTpl#
    @} 