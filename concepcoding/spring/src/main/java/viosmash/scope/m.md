bean scope

singleton: is default when create bean, it eager initialize(when app start, is created), it only one instance managed by ioc

prototype: Each time new Object is created, 
its initialized, means when object is created only when its required

request: new object will be created  for each http request, lazily initialized

session: new object will be created for each http session, lazily initialized, 
object will be created when user access to any endpoint

``Proxy mode: ScopeProxyMode.TARGET_CLASS``: create a proxy object for this class
