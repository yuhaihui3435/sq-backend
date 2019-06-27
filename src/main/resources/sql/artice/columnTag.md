sample
===

	select #use("cols")# from COLUMN_TAG_T columnTag where  #use("condition")#

sample$count
===
    select count(1) from COLUMN_TAG_T columnTag where #use("condition")#

cols
===
	columnTag.ID,columnTag.COLUMN_ID,columnTag.TAG_ID

updateSample
===

	columnTag.ID=#id#,columnTag.COLUMN_ID=#columnId#,columnTag.TAG_ID=#tagId#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(id)){
	 and columnTag.ID=#id#
	@}
	@if(!isEmpty(columnId)){
	 and columnTag.COLUMN_ID=#columnId#
	@}
	@if(!isEmpty(tagId)){
	 and columnTag.TAG_ID=#tagId#
	@}



