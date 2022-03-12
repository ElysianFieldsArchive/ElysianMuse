//[elysianmuse](../../../index.md)/[org.darkSolace.muse.statistics.service](../index.md)/[LastSeenService](index.md)/[prune](prune.md)

# prune

[jvm]\

@Scheduled(cron = "0/30 * * * * *")

@Transactional

fun [prune](prune.md)()

Removes timed-out [LastSeenEntry](../../org.darkSolace.muse.statistics.model/-last-seen-entry/index.md)s from the database This method is executed every 30 seconds
