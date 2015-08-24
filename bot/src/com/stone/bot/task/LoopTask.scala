package com.stone.bot.task

import com.stone.bot.BotTask

abstract class LoopTask(intervalTime:Long) extends BotTask {
  var lastRunTime:Long = 0
  var interval:Long = intervalTime
}