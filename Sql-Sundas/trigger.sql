Create or Replace trigger ChangeMonitor
before insert on CourseAllocation
Referencing old as o and new as n
For each row
declare

column_name	varchar2(30);
user_id		varchar2(30);

select USER into user_ID from dual;
select SYSDATE into mytimestamp from dual;

BEGIN 

IF(Updating('SecNo'))
	insert into stagingTable(StudID, SemesterID, CourseID, colum_name, old_value, new_value, user_ID, changeTime)
		values(o.StudID, o.SemesterID, o.CourseID, 'SecNo', :o.SecNo, :n.SecNo, user_ID, :mytimestamp);
end IF;


IF(Updating('BNo'))
	insert into stagingTable(colum_name, old_value, new_value, user_ID, changeTime)
		values('BNo', :o.BNo, :n.BNo, user_ID, :mytimestamp);
end IF;

IF(Updating('RoomNo'))
	insert into stagingTable(colum_name, old_value, new_value, user_ID, changeTime)
		values('RoomNo', :o.RoomNo, :n.RoomNo, user_ID, :mytimestamp);
end IF;

IF(Updating('Marks'))
	insert into stagingTable(colum_name, old_value, new_value, user_ID, changeTime)
		values('Marks', :o.Marks, :n.Marks, user_ID, :mytimestamp);
end IF;


IF(Updating('LGrade'))
	insert into stagingTable(colum_name, old_value, new_value, user_ID, changeTime)
		values('LGrade', :o.LGrade, :n.LGrade, user_ID, :mytimestamp);
end IF;

end;
/