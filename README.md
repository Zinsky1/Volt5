# Volt5


в UserServiceImp теперь не вызывается циклическая зависимость.
  (добавил в WebSecurityConfig-e анатацию @Lazy в конструкоре, где инжектится UserService,
  а в AppRunner заменил UserService на EntityManager, чтоб давать админу не только роль Юзера)
