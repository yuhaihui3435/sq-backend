sample
===

	select #use("cols")# from DOCTOR_PIC_T doctorPic where  #use("condition")#
	@orm.single({"picId":"id"},"com.neuray.wp.entity.FileMap");

sample$count
===
    select count(1) from DOCTOR_PIC_T doctorPic where #use("condition")#

cols
===
	doctorPic.TYPE,doctorPic.EXT,doctorPic.PIC_ID,doctorPic.DOCTOR_ID,doctorPic.ID,doctorPic.DESCRIBE

updateSample
===

	doctorPic.TYPE=#type#,doctorPic.EXT=#ext#,doctorPic.PIC_ID=#picId#,doctorPic.DOCTOR_ID=#doctorId#,doctorPic.ID=#id#,doctorPic.DESCRIBE=#describe#

condition
===

	1 = 1
	@if(!isEmpty(type)){
	 and doctorPic.TYPE=#type#
	@}
	@if(!isEmpty(ext)){
	 and doctorPic.EXT=#ext#
	@}
	@if(!isEmpty(picId)){
	 and doctorPic.PIC_ID=#picId#
	@}
	@if(!isEmpty(doctorId)){
	 and doctorPic.DOCTOR_ID=#doctorId#
	@}
	@if(!isEmpty(id)){
	 and doctorPic.ID=#id#
	@}
	@if(!isEmpty(describe)){
	 and doctorPic.DESCRIBE=#describe#
	@}



