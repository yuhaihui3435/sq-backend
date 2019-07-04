sample
===

	select #use("cols")# from ARTICE_TAG_T articeTag where  #use("condition")#
	@orm.single({"tagId":"id"},"com.neuray.wp.entity.DictItem");

sample$count
===
    select count(1) from ARTICE_TAG_T articeTag where #use("condition")#

cols
===
	articeTag.ID,articeTag.ARTICE_ID,articeTag.TAG_ID

updateSample
===

	articeTag.ID=#id#,articeTag.ARTICE_ID=#articeId#,articeTag.TAG_ID=#tagId#

condition
===

	1 = 1 
	@if(!isEmpty(id)){
	 and articeTag.ID=#id#
	@}
	@if(!isEmpty(articeId)){
	 and articeTag.ARTICE_ID=#articeId#
	@}
	@if(!isEmpty(tagId)){
	 and articeTag.TAG_ID=#tagId#
	@}



