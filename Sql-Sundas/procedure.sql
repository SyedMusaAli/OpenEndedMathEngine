CREATE OR REPLACE PROCEDURE undoStep(tillDate IN DATE) IS

cursor c_tab is Select StudId, SemesterID, CourseID, columnName, old_value from stagingTable where changeTime >= tillDate;

BEGIN


for v_c in c_tab loop

update CourseAllocation
set v_c.columnName = v_c.old_value
where StudId = v_c.StudId and SemesterID = v_c.SemesterID and CourseID = v_c.CourseID;

end loop;

END;
