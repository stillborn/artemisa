function handleRequest(xhr, status, args, name)
{
	if(args.success){
		PF(name).hide();
	}
}