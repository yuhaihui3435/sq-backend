sample
===

	select #use("cols")# from DOCTOR_TAG_T doctorTag where  #use("condition")#
	@orm.single({"tagId":"id"},"com.neuray.wp.entity.DictItem");

sample$count
===
    select count(1) from DOCTOR_TAG_T doctorTag where #use("condition")#

cols
===
	doctorTag.TAG_ID,doctorTag.TYPE,doctorTag.DOCTOR_ID,doctorTag.ID

updateSample
===

	doctorTag.TAG_ID=#tagId#,doctorTag.TYPE=#type#,doctorTag.DOCTOR_ID=#doctorId#,doctorTag.ID=#id#

condition
===

	1 = 1
	@if(!isEmpty(tagId)){
	 and doctorTag.TAG_ID=#tagId#
	@}
	@if(!isEmpty(type)){
	 and doctorTag.TYPE=#type#
	@}
	@if(!isEmpty(doctorId)){
	 and doctorTag.DOCTOR_ID=#doctorId#
	@}
	@if(!isEmpty(id)){
	 and doctorTag.ID=#id#
	@}



