sample
===

	select #use("cols")# from QA_T qa where  #use("condition")#

sample$count
===
    select count(1) from QA_T qa where #use("condition")#

cols
===
	qa.AUTHOR_ID,qa.TITLE,qa.CONTENT,qa.REPORT_STATUS,qa.ANONYMOUS,qa.ID,qa.CR_AT

updateSample
===

	qa.AUTHOR_ID=#authorId#,qa.TITLE=#title#,qa.CONTENT=#content#,qa.REPORT_STATUS=#reportStatus#,qa.ANONYMOUS=#anonymous#,qa.ID=#id#,qa.CR_AT=#crAt#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(authorId)){
	 and qa.AUTHOR_ID=#authorId#
	@}
	@if(!isEmpty(title)){
	 and qa.TITLE=#title#
	@}
	@if(!isEmpty(content)){
	 and qa.CONTENT=#content#
	@}
	@if(!isEmpty(reportStatus)){
	 and qa.REPORT_STATUS=#reportStatus#
	@}
	@if(!isEmpty(anonymous)){
	 and qa.ANONYMOUS=#anonymous#
	@}
	@if(!isEmpty(id)){
	 and qa.ID=#id#
	@}
	@if(!isEmpty(crAt)){
	 and qa.CR_AT=#crAt#
	@}



