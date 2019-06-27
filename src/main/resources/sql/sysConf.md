sample
===

	select #use("cols")# from SYS_CONF_T  where  #use("condition")#

sample$count
===
    select count(1) from SYS_CONF_T where #use("condition")#

cols
===
	UP_BY,SC_NAME,SC_VAL,SC_KEY,UP_AT

updateSample
===

	UP_BY=#upBy#,SC_NAME=#scName#,SC_VAL=#scVal#,SC_KEY=#scKey#,UP_AT=#upAt#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(upBy)){
	 and UP_BY=#upBy#
	@}
	@if(!isEmpty(scName)){
	 and SC_NAME=#scName#
	@}
	@if(!isEmpty(scVal)){
	 and SC_VAL=#scVal#
	@}
	@if(!isEmpty(scKey)){
	 and SC_KEY=#scKey#
	@}
	@if(!isEmpty(upAt)){
	 and UP_AT=#upAt#
	@}



