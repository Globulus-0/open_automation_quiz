Feature: 提交企业复工申请资料
  为了能够提交企业复工申请资料
  我希望能够打开申请表并填写所需信息

  Scenario: 执行
    Given 打开浏览器
    Then 等待 1 秒
    When 用户打开企业复工申请资料提交表 "https://templates.jinshuju.net/detail/Dv9JPD"
    Then 等待 3 秒
    When 滑动到底部
    When 用户在第一页选择 "连续生产/开工类企事业单位"
    Then 选中了 "连续生产/开工类企事业单位"
    And 用户截图 "第一页截图"
    And 用户点击下一页按钮
    Then 等待 3 秒
    When 滑动到底部
    And 填写申请日期运行脚本的当年元旦日期
    And 填写申请人 "Globulus"
    And 填写联系方式 "17302234165"
    And 用户截图 "第二页截图"
    And 用户点击下一页按钮
    Then 等待 3 秒
    When 滑动到底部
    And 填写单位 "测试公司"
    And 填写人数 99
    And 填写时间测试时间
    And 填写湖北籍员工、前往湖北以及与湖北人员密切接触的员工（人数） 0
    And 填写单位负责人 "您的姓名"
    And 填写新联系方式 "17302234165"
    And 填写疫情防控方案 "测试内容"
    And 用户截图 "第三页截图"
    And 点击提交
    Then 等待 3 秒
    Then 提交成功验证
    And 用户截图 "提交成功截图"
    Then 关闭浏览器