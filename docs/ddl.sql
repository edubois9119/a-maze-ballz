CREATE TABLE IF NOT EXISTS `Maze`
(
    `maze_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `grid_rows`    INTEGER                           NOT NULL,
    `grid_columns` INTEGER                           NOT NULL,
    `walls`        TEXT,
    `level`        INTEGER                           NOT NULL,
    `entrance_x`   INTEGER,
    `entrance_y`   INTEGER,
    `exit_x`       INTEGER,
    `exit_y`       INTEGER
);

CREATE TABLE IF NOT EXISTS `Attempt`
(
    `attempt_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `maze_id`    INTEGER                           NOT NULL,
    `user_id`    INTEGER                           NOT NULL,
    `solved`     INTEGER,
    `time_spent` INTEGER                           NOT NULL,
    FOREIGN KEY (`maze_id`) REFERENCES `Maze` (`maze_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS `index_Attempt_maze_id` ON `Attempt` (`maze_id`);
CREATE INDEX IF NOT EXISTS `index_Attempt_user_id` ON `Attempt` (`user_id`);
CREATE INDEX IF NOT EXISTS `index_Attempt_solved` ON `Attempt` (`solved`);
CREATE INDEX IF NOT EXISTS `index_Attempt_time_spent` ON `Attempt` (`time_spent`);

CREATE TABLE IF NOT EXISTS `User`
(
    `user_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `oauth_key` TEXT
);
CREATE UNIQUE INDEX `index_User_oauth_key` ON `User` (`oauth_key`);

