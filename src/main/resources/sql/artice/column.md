sample
===

	select #use("cols")# from COLUMN_T column where  #use("condition")#

sample$count
===
    select count(1) from COLUMN_T column where #use("condition")#

cols
===
	column.ID,column.DE_AT,column.PARENT_ID,column.DE_BY,column.UP_AT,column.CR_BY,column.CR_AT,column.UP_BY,column.NAME

updateSample
===

	column.ID=##,column.DE_AT=#de#,column.PARENT_ID=#parent#,column.DE_BY=#de#,column.UP_AT=#up#,column.CR_BY=#cr#,column.CR_AT=#cr#,column.UP_BY=#up#,column.NAME=#na#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty()){
	 and column.ID=##
	@}
	@if(!isEmpty(de)){
	 and column.DE_AT=#de#
	@}
	@if(!isEmpty(parent)){
	 and column.PARENT_ID=#parent#
	@}
	@if(!isEmpty(de)){
	 and column.DE_BY=#de#
	@}
	@if(!isEmpty(up)){
	 and column.UP_AT=#up#
	@}
	@if(!isEmpty(cr)){
	 and column.CR_BY=#cr#
	@}
	@if(!isEmpty(cr)){
	 and column.CR_AT=#cr#
	@}
	@if(!isEmpty(up)){
	 and column.UP_BY=#up#
	@}
	@if(!isEmpty(na)){
	 and column.NAME=#na#
	@}



