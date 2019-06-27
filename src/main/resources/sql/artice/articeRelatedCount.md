sample
===

	select #use("cols")# from ARTICE_RELATED_COUNT_T articeRelatedCount where  #use("condition")#

sample$count
===
    select count(1) from ARTICE_RELATED_COUNT_T articeRelatedCount where #use("condition")#

cols
===
	articeRelatedCount.ID,articeRelatedCount.ARTICE_ID,articeRelatedCount.VERSION,articeRelatedCount.TYPE,articeRelatedCount.VAL

updateSample
===

	articeRelatedCount.ID=#id#,articeRelatedCount.ARTICE_ID=#articeId#,articeRelatedCount.VERSION=#version#,articeRelatedCount.TYPE=#type#,articeRelatedCount.VAL=#val#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(id)){
	 and articeRelatedCount.ID=#id#
	@}
	@if(!isEmpty(articeId)){
	 and articeRelatedCount.ARTICE_ID=#articeId#
	@}
	@if(!isEmpty(version)){
	 and articeRelatedCount.VERSION=#version#
	@}
	@if(!isEmpty(type)){
	 and articeRelatedCount.TYPE=#type#
	@}
	@if(!isEmpty(val)){
	 and articeRelatedCount.VAL=#val#
	@}



