package org.ps.example.demo01;

import org.ps.platform.core.ShardTask;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "demo_shard_task")
public class DemoShardTask extends ShardTask{



}
