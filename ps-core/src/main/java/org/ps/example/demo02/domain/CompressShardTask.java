package org.ps.example.demo02.domain;

import org.ps.platform.core.ShardTask;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comparess_shard_task")
public class CompressShardTask extends ShardTask {

}
