CREATE OR REPLACE PROCEDURE undo(tillDate IN DATE) IS

cursor c_tab = Select StudId, SemesterID, CourseID, column_name, old_value into  from stagingTable where changeTime >= tillDate;

BEGIN


for v_c in c_tab loop

update CourseAllocation
	set :v_c.column_name = :v_c.old_value
	where StudId = :v_c.StudId and SemesterID = :v_c.SemesterID and CourseID = :v_c.CourseID;

end loop;

END;