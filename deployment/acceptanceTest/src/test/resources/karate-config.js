function fn() {
  const uuid = java.util.UUID.randomUUID();
  const config = {
    headers: {
      'Client-Id': '#{clientId}#',
      'Client-Secret': '#{clientSecret}#',
      'Message-Id': uuid,
      'Another-Header': 'value'
    },
    oasUrl: 'co/com/mueblessas/pet-store.yaml',
    urlBase: 'http://localhost:8080/api/v1'
  };

  karate.configure('connectTimeout', 2000);
  karate.configure('readTimeout', 2000);
  karate.configure('ssl', true);
  return config;
}