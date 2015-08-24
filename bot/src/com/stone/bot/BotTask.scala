package com.stone.bot

abstract class BotTask() {
  
  /**
   * Run strategy;
   */
  def runOnceTime(bot: CrazyBot)
}