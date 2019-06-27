sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T where id=d.cr_by) createUserName,
                         (select SU_NAME from SYS_USER_T where id=d.up_by) updateUserName,
                         (select SU_NAME from SYS_USER_T where id=d.de_by) delUserName,
                         (select dict_name from DICT_T where id=d.dict_id) dictName
                          from DICT_ITEM_T d where  #use("condition")#

special
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T where id=d.cr_by) createUserName,
                         (select SU_NAME from SYS_USER_T where id=d.up_by) updateUserName,
                         (select SU_NAME from SYS_USER_T where id=d.de_by) delUserName,
                         (select dict_name from DICT_T where id=d.dict_id) dictName
                          from DICT_ITEM_T d  where  #use("scondition")#

special$count
===
    select count(1) from DICT_ITEM_T d where #use("scondition")#

sample$count
===
    select count(1) from DICT_ITEM_T d where #use("condition")#

cols
===
	DE_AT,CR_AT,CR_BY,UP_BY,UP_AT,ITEM_NAME,DE_BY,ITEM_VAL,DICT_ID,ID

updateSample
===

	DE_AT=#deAt#,CR_AT=#crAt#,CR_BY=#crBy#,UP_BY=#upBy#,UP_AT=#upAt#,ITEM_NAME=#itemName#,DE_BY=#deBy#,ITEM_VAL=#itemVal#,DICT_ID=#dictId#

scondition
===

	1 = 1 and d.DE_AT is null
	@if(!isEmpty(deAt)){
	 and d.DE_AT=#deAt#
	@}
	@if(!isEmpty(crAt)){
	 and d.CR_AT=#crAt#
	@}
	@if(!isEmpty(crBy)){
	 and d.CR_BY=#crBy#
	@}
	@if(!isEmpty(upBy)){
	 and d.UP_BY=#upBy#
	@}
	@if(!isEmpty(upAt)){
	 and d.UP_AT=#upAt#
	@}
	@if(!isEmpty(itemName)){
	 and (d.ITEM_NAME=#itemName# or d.ITEM_VAL=#itemName#)
	@}
	@if(!isEmpty(deBy)){
	 and d.DE_BY=#deBy#
	@}
	@if(!isEmpty(dictId)){
	 and d.DICT_ID=#dictId#
	@}
    @if(!isEmpty(order!)){
     #order#
    @}else{
     order by d.UP_AT desc
    @}


condition
===

	1 = 1 and d.DE_AT is null
    	@if(!isEmpty(deBy)){
    	 and d.DE_BY=#deBy#
    	@}
    	@if(!isEmpty(itemVal)){
    	 and d.ITEM_VAL=#itemVal#
    	@}
    	@if(!isEmpty(itemName)){
    	 and d.ITEM_NAME=#itemName#
    	@}
    	@if(!isEmpty(dictId)){
    	 and d.DICT_ID=#dictId#
    	@}
    	@if(!isEmpty(crAt)){
    	 and d.CR_AT=#crAt#
    	@}
    	@if(!isEmpty(crBy)){
    	 and d.CR_BY=#crBy#
    	@}
    	@if(!isEmpty(upAt)){
    	 and d.UP_AT=#upAt#
    	@}
    	@if(!isEmpty(deAt)){
    	 and d.DE_AT=#deAt#
    	@}
    	@if(!isEmpty(upBy)){
    	 and d.UP_BY=#upBy#
    	@}
        @if(!isEmpty(order!)){
         #order#
        @}else{
         order by d.UP_AT desc
        @}

