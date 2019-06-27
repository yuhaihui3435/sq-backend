sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T where id=d.cr_by) createUserName,
                                     (select SU_NAME from SYS_USER_T where id=d.up_by) updateUserName,
                                     (select SU_NAME from SYS_USER_T where id=d.de_by) delUserName
                                      from DICT_T d  where  #use("condition")#

sample$count
===
    select count(1) from DICT_T where #use("condition")#

cols
===
	DICT_VAL,DICT_NAME,CR_BY,CR_AT,UP_BY,UP_AT,DE_BY,DE_AT,ID

updateSample
===

	DICT_VAL=#dictVal#,DICT_NAME=#dictName#,CR_BY=#crBy#,CR_AT=#crAt#,UP_BY=#upBy#,UP_AT=#upAt#,DE_BY=#deBy#,DE_AT=#deAt#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(dictVal)){
	 and DICT_VAL=#dictVal#
	@}
	@if(!isEmpty(dictName)){
	 and DICT_NAME=#dictName#
	@}
	@if(!isEmpty(crAt)){
     and CR_AT=#crAt#
    @}
    @if(!isEmpty(crBy)){
     and CR_BY=#crBy#
    @}
    @if(!isEmpty(upAt)){
     and UP_AT=#upAt#
    @}
    @if(!isEmpty(upBy)){
     and UP_BY=#upBy#
    @}
    @if(!isEmpty(deAt)){
     and DE_AT=#deAt#
    @}
    @if(!isEmpty(deBy)){
     and DE_BY=#deBy#
    @}
    @if(!isEmpty(order!)){
     #order#
    @}else{
     order by UP_AT desc
    @}



