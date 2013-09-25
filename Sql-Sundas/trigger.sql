Create or Replace trigger ChangeMonitor
before update on CourseAllocation
Referencing old as o new as n
For each row
declare

column_name	varchar2(30);
user_id		varchar2(30);
mytimestamp	date;

BEGIN 

select USER into user_ID from dual;
select SYSDATE into mytimestamp from dual;

IF(Updating('SecNo'))
then
	insert into stagingTable(StudID, SemesterID, CourseID, columnName, old_value, new_value, user_ID, changeTime)
		values(:o.StudID, :o.SemesterID, :o.CourseID, 'SecNo', :o.SecNo, :n.SecNo, user_ID, mytimestamp);
end IF;


IF(Updating('BNo'))
then
	insert into stagingTable(StudID, SemesterID, CourseID, columnName, old_value, new_value, user_ID, changeTime)
		values(:o.StudID, :o.SemesterID, :o.CourseID, 'BNo', :o.SecNo, :n.SecNo, user_ID, mytimestamp);
end IF;

IF(Updating('RoomNo'))
then
	insert into stagingTable(StudID, SemesterID, CourseID, columnName, old_value, new_value, user_ID, changeTime)
		values(:o.StudID, :o.SemesterID, :o.CourseID, 'RoomNo', :o.SecNo, :n.SecNo, user_ID, mytimestamp);
end IF;

IF(Updating('Marks'))
then
	insert into stagingTable(StudID, SemesterID, CourseID, columnName, old_value, new_value, user_ID, changeTime)
		values(:o.StudID, :o.SemesterID, :o.CourseID, 'MarksNo', :o.SecNo, :n.SecNo, user_ID, mytimestamp);
end IF;


IF(Updating('LGrade'))
then
	insert into stagingTable(StudID, SemesterID, CourseID, columnName, old_value, new_value, user_ID, changeTime)
		values(:o.StudID, :o.SemesterID, :o.CourseID, 'LGrade', :o.SecNo, :n.SecNo, user_ID, mytimestamp);
end IF;

end;
/
