package ylss.model.constant;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	public static String partner = "2088021327130674";
	// 商户的私
	public static String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL/JDjJfPTN4OzVBijOYNnDyOt7YHJvqLRSrBwZuEFElHSntyE3dWtFcSN6YYTR9OYP801JIh6bp5V9Pz/vgUT5UksTnLVo2XaJJfEBJ1NRFEQEAT/g1efv3rqEr/DXrsJL9aVmpWfEH1KbXtqVJ/M4rZe3FYYde5kc652myF7CNAgMBAAECgYBhcrN3/NSIFotfIBIs7SacdThcisrenjqRyeUj60D3Ojh7WYrxpYvD3XRZRmv907Mp0Yk+wgPLWAVoTJGNlgkxFOYz8L5qwezoChsGldrPmn2EPERiAMETYynmnhP5GrvrgANVMUiBtUcA9iNLHVi2fNrg/lGCpPzKmQgkxPnlsQJBAO2d1weL+eFYC9Wx7Ga2NY29boBWcTlyPo0Nll8ReEZZtSXkWkoGS3jVVxOF6f8e3BfKHGkWqpO2gxzsvxyYRfsCQQDOn39FbZCrbxvy689YPQ1RCnTvSaVzIo2wg//yEx9xdHZduYqTh0j0UMirZz45ZuFGTl+K8+w2HH0RoIWkyoUXAkAUrHT2/SDCkGyHLTHKRfQm3uJsXBkBzITQtOvuXZC/XjK6l0Gbyyxp2PX4huAzCDZSA42xPv2cWHbT321JSZ5dAkBFxiChXl1zNuMWRkFPh9ZeXoycXZ9MYQe96uVcZs7AblamUciVByNAtaL+FrAxjCdYqZCkCLjkkPw/HpjizCzjAkARqj9QOg1Ak+X2QQBwvgeXn0xpl6eIi3XwOacOEtClQACCUuJFthbGfjele6aUHWAYu6BnGQjBi1/rFCEyr3EP";

	// 支付宝的公钥，无需修改该值
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/yQ4yXz0zeDs1QYozmDZw8jre2Byb6i0UqwcGbhBRJR0p7chN3VrRXEjemGE0fTmD/NNSSIem6eVfT8/74FE+VJLE5y1aNl2iSXxASdTURREBAE/4NXn7966hK/w167CS/WlZqVnxB9Sm17alSfzOK2XtxWGHXuZHOudpshewjQIDAQAB";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "/root/ylss/";
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
