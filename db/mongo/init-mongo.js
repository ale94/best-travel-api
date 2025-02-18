db.createUser({
        user: 'root',
        pwd: 'toor',
        roles: [
            {
                role: 'readWrite',
                db: 'testDB',
            },
        ],
    });
db.createCollection('app_users', { capped: false });

db.app_users.insert([
    { 
        "username": "ragnar777", 
        "dni": "VIKI771012HMCRG093", 
        "enabled": true, 
        "password": "$2a$10$yIaVC1FmyfRF9Ztgh5pBsedBsC5tTT/hrpCZqfM4j.i6z.MT8dSsi",
        "role": 
        {
            "granted_authorities": ["ROLE_USER"]
        } 
    },
    { 
        "username": "heisenberg", 
        "dni": "BBMB771012HMCRR022", 
        "enabled": true, 
        "password": "$2a$10$JcW8uEOAL01xtSer7z6Wje9IuoBhPSKzxcAciNDs1zq3fndImutyq",
        "role": 
        {
            "granted_authorities": ["ROLE_USER"]
        } 
    },
    { 
        "username": "misterX", 
        "dni": "GOTW771012HMRGR087", 
        "enabled": true, 
        "password": "$2a$10$uynATXA7J7RwJOTq4pqsxOYKSRe7xPmaxJHxI0R9VskzkeqC3vAjq",
        "role": 
        {
            "granted_authorities": ["ROLE_USER", "ROLE_ADMIN"]
        } 
    },
    { 
        "username": "neverMore", 
        "dni": "WALA771012HCRGR054", 
        "enabled": true, 
        "password": "$2a$10$HOczc52elM.qDei8OlRfyO1HJaiLilH7yYzeejYbbkLaHxdQP4K4C",
        "role": 
        {
            "granted_authorities": ["ROLE_ADMIN"]
        } 
    }
]);