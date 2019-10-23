CREATE TABLE IF NOT EXISTS `Maze`
(
    `maze_id`    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `rows`       INTEGER                           NOT NULL,
    `columns`    INTEGER                           NOT NULL,
    `walls`      TEXT,
    `difficulty` INTEGER                           NOT NULL,
    `entrance_x` INTEGER,
    `entrance_y` INTEGER,
    `exit_x`     INTEGER,
    `exit_y`     INTEGER
);
CREATE TABLE IF NOT EXISTS `Attempt`
(
    `attempt_id`       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `maze_id`          INTEGER                           NOT NULL,
    `user_id`          INTEGER                           NOT NULL,
    `maze_started`     INTEGER                           NOT NULL,
    `maze_ended`       INTEGER                           NOT NULL,
    `maze_pause_start` INTEGER                           NOT NULL,
    `maze_pause_end`   INTEGER                           NOT NULL,
    `solved`           INTEGER,
    `num_Attempts`     INTEGER                           NOT NULL,
    FOREIGN KEY (`maze_id`) REFERENCES `Maze` (`maze_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE INDEX `index_Attempt_maze_id` ON `Attempt` (`maze_id`);
CREATE INDEX `index_Attempt_user_id` ON `Attempt` (`user_id`);
CREATE INDEX `index_Attempt_maze_started` ON `Attempt` (`maze_started`);
CREATE INDEX `index_Attempt_maze_ended` ON `Attempt` (`maze_ended`);
CREATE INDEX `index_Attempt_maze_pause_start` ON `Attempt` (`maze_pause_start`);
CREATE INDEX `index_Attempt_maze_pause_end` ON `Attempt` (`maze_pause_end`);
CREATE INDEX `index_Attempt_solved` ON `Attempt` (`solved`);
CREATE INDEX `index_Attempt_num_Attempts` ON `Attempt` (`num_Attempts`);
CREATE TABLE IF NOT EXISTS `User`
(
    `user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL
);

