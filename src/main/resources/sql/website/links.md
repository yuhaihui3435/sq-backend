sample
===

	select #use("cols")# from LINKS_T links where  #use("condition")#

sample$count
===
    select count(1) from LINKS_T links where #use("condition")#

cols
===
	links.DESCRIBE,links.URL,links.ID,links.IMG,links.NAME

updateSample
===

	links.DESCRIBE=#describe#,links.URL=#url#,links.ID=#id#,links.IMG=#img#,links.NAME=#name#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(describe)){
	 and links.DESCRIBE=#describe#
	@}
	@if(!isEmpty(url)){
	 and links.URL=#url#
	@}
	@if(!isEmpty(id)){
	 and links.ID=#id#
	@}
	@if(!isEmpty(img)){
	 and links.IMG=#img#
	@}
	@if(!isEmpty(name)){
	 and links.NAME=#name#
	@}



