package org.ps.example.demo03.domain;

import org.ps.platform.core.ShardTask;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "analysis_shard_task")
public class AnalysisShardTask extends ShardTask {
}
