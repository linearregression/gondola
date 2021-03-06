/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the New BSD License.
 * See the accompanying LICENSE file for terms.
 */

package com.yahoo.gondola.container;

import com.google.common.collect.Range;

/**
 * The interface Shard manager protocol.
 */
public interface ShardManagerProtocol {

    /**
     * Start observing.
     *
     * @param shardId         the shard id
     * @param observedShardId the observed shard id
     * @throws ShardManagerException the shard manager exception
     */
    void startObserving(String shardId, String observedShardId, long timeoutMs)
        throws ShardManagerException, InterruptedException;

    /**
     * Stop observing.
     *
     * @param shardId         the shard id
     * @param observedShardId the observed shard id
     * @throws ShardManagerException the shard manager exception
     */
    void stopObserving(String shardId, String observedShardId, long timeoutMs)
        throws ShardManagerException, InterruptedException;

    /**
     * Assign bucket.
     *
     * @param splitRange the split range
     * @param toShardId  the to shard id
     * @param timeoutMs  the timeout ms
     * @throws ShardManagerException the shard manager exception
     */
    void migrateBuckets(Range<Integer> splitRange, String fromShardId, String toShardId,
                        long timeoutMs) throws ShardManagerException, InterruptedException;

    /**
     * Wait slave until slave synced.
     *
     * @param shardId   the slave shard id
     * @param timeoutMs the timeout ms
     * @return the boolean
     * @throws ShardManagerException the shard manager exception
     */
    boolean waitSlavesSynced(String shardId, long timeoutMs)
        throws ShardManagerException, InterruptedException;

    /**
     * Wait approaching boolean.
     *
     * @param shardId   the shard id
     * @param timeoutMs the timeout ms
     * @return the boolean
     * @throws ShardManagerException the shard manager exception
     */
    boolean waitSlavesApproaching(String shardId, long timeoutMs)
        throws ShardManagerException, InterruptedException;

    /**
     * Sets buckets.
     *
     * @param splitRange        the split range
     * @param fromShardId       the from shard id
     * @param toShardId         the to shard id
     * @param migrationComplete flag to indicate the migration is complete
     */
    void setBuckets(Range<Integer> splitRange, String fromShardId, String toShardId, boolean migrationComplete)
        throws ShardManagerException, InterruptedException;

    /**
     * rollback buckets to original state.
     *
     * @param splitRange
     */
    void rollbackBuckets(Range<Integer> splitRange) throws ShardManagerException, InterruptedException;

    /**
     * The type Shard manager exception.
     */
    class ShardManagerException extends Exception {

        public ShardManagerException(Exception e) {
            super(e);
        }

        public enum CODE {
            FAILED_START_SLAVE,
            FAILED_STOP_SLAVE,
            FAILED_BUCKET_ROLLBACK,
            FAILED_SET_BUCKETS,
            FAILED_MIGRATE_1,
            MASTER_IS_GONE,
            SLAVE_NOT_SYNC,

        }

        public CODE errorCode;

        public ShardManagerException(CODE code) {
            super(code.name());
            this.errorCode = code;
        }

        public ShardManagerException(CODE code, String message) {
            super(code.name() + "-" + message);
            this.errorCode = code;
        }
    }
}
