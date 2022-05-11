package de.mannodermaus.junit5.internal.runners

import org.junit.platform.engine.FilterResult
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.launcher.PostDiscoveryFilter
import kotlin.math.abs

internal class ShardingFilter(private val numShards: Int, private val shardIndex: Int) :
    PostDiscoveryFilter {
    override fun apply(descriptor: TestDescriptor): FilterResult {
        if (descriptor.isTest) {
            val remainder = abs(descriptor.hashCode()) % numShards
            val runningShard = remainder == shardIndex
            return if (runningShard) {
                FilterResult.included(null)
            } else {
                FilterResult.excluded("excluded")
            }
        }

        for (each in descriptor.children) {
            return if (apply(each).included()) {
                FilterResult.included(null)
            } else {
                FilterResult.excluded("excluded")
            }
        }

        return FilterResult.excluded("excluded")
    }
}
