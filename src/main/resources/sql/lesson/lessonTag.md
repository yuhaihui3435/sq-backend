sample
===

	select #use("cols")# from LESSON_TAG_T lessonTag where  #use("condition")#

sample$count
===
    select count(1) from LESSON_TAG_T lessonTag where #use("condition")#

cols
===
	lessonTag.LESSON_ID,lessonTag.TAG_ID,lessonTag.ID

updateSample
===

	lessonTag.LESSON_ID=#lessonId#,lessonTag.TAG_ID=#tagId#,lessonTag.ID=#id#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(lessonId)){
	 and lessonTag.LESSON_ID=#lessonId#
	@}
	@if(!isEmpty(tagId)){
	 and lessonTag.TAG_ID=#tagId#
	@}
	@if(!isEmpty(id)){
	 and lessonTag.ID=#id#
	@}



