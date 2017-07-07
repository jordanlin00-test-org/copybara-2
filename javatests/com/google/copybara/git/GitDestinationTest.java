import com.google.copybara.Destination.Reader;
  private boolean force;
    force = false;
    options.setForce(force);
    checkPreviousImportReference();
  }

  @Test
  public void previousImportReference_with_force() throws Exception {
    force = true;
    checkPreviousImportReference();
  }

  private void checkPreviousImportReference()
      throws IOException, ValidationException, RepoException {
    Writer writer =
  /**
   * This test reproduces an issue where the author timestamp has subseconds and, as a result,
   * before the fix the change was committed with the (incorrect) date '2017-04-12T12:19:00-07:00',
   * instead of '2017-06-01T12:19:00-04:00'.
   */
  @Test
  public void authorDateWithSubsecondsCorrectlyPopulated() throws Exception {
    fetch = "master";
    push = "master";

    Files.write(workdir.resolve("test.txt"), "some content".getBytes());

    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(1496333940012L), ZoneId.of("-04:00"));
    DummyRevision firstCommit = new DummyRevision("first_commit")
        .withAuthor(new Author("Foo Bar", "foo@bar.com"))
        .withTimestamp(zonedDateTime);
    process(
        firstCommitWriter(),
        firstCommit);

    String authorDate = git("log", "-1", "--pretty=%aI");

    assertThat(authorDate).isEqualTo("2017-06-01T12:19:00-04:00\n");
  }


  @Test
  public void testMapReferences() throws Exception {
    Files.write(workdir.resolve("test.txt"), "one".getBytes());
    process(firstCommitWriter(), new DummyRevision("1"));

    Files.write(workdir.resolve("test.txt"), "two".getBytes());
    GitDestination destination = destination();
    Writer writer = destination.newWriter(destinationFiles, /*dryRun=*/ false);
    process(writer, new DummyRevision("2"));

    Files.write(workdir.resolve("test.txt"), "three".getBytes());
    process(writer, new DummyRevision("3"));

    Reader<GitRevision> reader = destination.newReader(destinationFiles);
    reader.visitChanges(/*start=*/ null, ignore -> VisitResult.CONTINUE);

    Files.write(workdir.resolve("test.txt"), "four".getBytes());
    process(writer, new DummyRevision("4"));
  }