if redis.call('setnx', kEYS[1], ARGV[1]) == 1 then
    return redis.call("expire", kEYS[1], ARGV[2])
else
    return -1;
end