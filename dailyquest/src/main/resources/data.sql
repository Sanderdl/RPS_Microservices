INSERT INTO dailyquest_rps.quests (description, name, evaluation) VALUES('Win by playing rock', 'Rock winner', '#{type} == 1 && #{progress} >= 1');
INSERT INTO dailyquest_rps.quests (description, name, evaluation) VALUES('Win by playing paper', 'Paper winner', '#{type} == 3 && #{progress} >= 1');
INSERT INTO dailyquest_rps.quests (description, name, evaluation) VALUES('Win by playing Scissors', 'Scissors winner', '#{type} == 2 && #{progress} >= 1');


