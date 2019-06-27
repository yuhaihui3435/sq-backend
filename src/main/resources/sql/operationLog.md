sample
===

	select #use("colssp")#,
	(select SU_NAME from SYS_USER_T  where ID=operationLog.DE_BY) as deByName,
	(select SU_NAME from SYS_USER_T  where ID=operationLog.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=operationLog.UP_BY) as upByName,
	(select SU_NAME from SYS_USER_T  where ID=operationLog.SYS_USER_ID) as sysUserName
	from OPERATION_LOG_T operationLog where  #use("condition")#

sample$count
===
    select count(1) from OPERATION_LOG_T operationLog where #use("condition")#

cols
===
	operationLog.ACCESS_PATH,operationLog.ID,operationLog.CR_AT,operationLog.DE_BY,operationLog.ACCESS_LOGTYPE,operationLog.ACCESS_EXCEPTION,operationLog.ACCESS_SOURCE,operationLog.ACCESS_TIME,operationLog.DE_AT,operationLog.ACCESS_TYPE,operationLog.SYS_USER_ID,operationLog.ACCESS_RESULT,operationLog.ACCESS_DATA,operationLog.UP_AT,operationLog.CR_BY,operationLog.UP_BY,operationLog.ACCESS_DESC,operationLog.ACCESS_FUNCTION

colssp
===
	operationLog.ACCESS_PATH,operationLog.ID,operationLog.CR_AT,operationLog.DE_BY,operationLog.ACCESS_LOGTYPE,operationLog.ACCESS_SOURCE,operationLog.ACCESS_TIME,operationLog.DE_AT,operationLog.ACCESS_TYPE,operationLog.SYS_USER_ID,operationLog.UP_AT,operationLog.CR_BY,operationLog.UP_BY,operationLog.ACCESS_FUNCTION

queryAccessData
===
	select t.access_data from operation_log_t t where t.id=#id#

queryAccessResult
===
	select t.access_result from operation_log_t t where t.id=#id#

queryAccessException
===
	select t.access_exception from operation_log_t t where t.id=#id#

queryAccessDesc
===
	select t.access_desc from operation_log_t t where t.id=#id#

updateSample
===

	operationLog.ACCESS_PATH=#accessPath#,operationLog.ID=#id#,operationLog.CR_AT=#crAt#,operationLog.DE_BY=#deBy#,operationLog.ACCESS_LOGTYPE=#accessLogtype#,operationLog.ACCESS_EXCEPTION=#accessException#,operationLog.ACCESS_SOURCE=#accessSource#,operationLog.ACCESS_TIME=#accessime#,operationLog.DE_AT=#deAt#,operationLog.ACCESS_TYPE=#accessype#,operationLog.SYS_USER_ID=#sysUserId#,operationLog.ACCESS_RESULT=#accessResult#,operationLog.ACCESS_DATA=#accessData#,operationLog.UP_AT=#upAt#,operationLog.CR_BY=#crBy#,operationLog.UP_BY=#upBy#,operationLog.ACCESS_DESC=#accessDesc#,operationLog.ACCESS_FUNCTION=#accessFunction#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(accessPath)){
	 and operationLog.ACCESS_PATH=#accessPath#
	@}
	@if(!isEmpty(id)){
	 and operationLog.ID=#id#
	@}
	@if(!isEmpty(crAt)){
	 and operationLog.CR_AT=#crAt#
	@}
	@if(!isEmpty(deBy)){
	 and operationLog.DE_BY=#deBy#
	@}
	@if(!isEmpty(accessLogtype)){
	 and operationLog.ACCESS_LOGTYPE=#accessLogtype#
	@}
	@if(!isEmpty(accessException)){
	 and operationLog.ACCESS_EXCEPTION=#accessException#
	@}
	@if(!isEmpty(accessSource)){
	 and operationLog.ACCESS_SOURCE=#accessSource#
	@}
	@if(!isEmpty(accessime)){
	 and operationLog.ACCESS_TIME=#accessTime#
	@}
	@if(!isEmpty(deAt)){
	 and operationLog.DE_AT=#deAt#
	@}
	@if(!isEmpty(accessype)){
	 and operationLog.ACCESS_TYPE=#accessType#
	@}
	@if(!isEmpty(sysUserId)){
	 and operationLog.SYS_USER_ID=#sysUserId#
	@}
	@if(!isEmpty(accessResult)){
	 and operationLog.ACCESS_RESULT=#accessResult#
	@}
	@if(!isEmpty(accessData)){
	 and operationLog.ACCESS_DATA=#accessData#
	@}
	@if(!isEmpty(upAt)){
	 and operationLog.UP_AT=#upAt#
	@}
	@if(!isEmpty(crBy)){
	 and operationLog.CR_BY=#crBy#
	@}
	@if(!isEmpty(upBy)){
	 and operationLog.UP_BY=#upBy#
	@}
	@if(!isEmpty(accessDesc)){
	 and operationLog.ACCESS_DESC=#accessDesc#
	@}
	@if(!isEmpty(accessFunction)){
	 and operationLog.ACCESS_FUNCTION=#accessFunction#
	@}



